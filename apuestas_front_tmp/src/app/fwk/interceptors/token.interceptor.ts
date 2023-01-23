import { Injectable } from '@angular/core';
import {
  HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse
} from '@angular/common/http';

import { Observable, Subject, BehaviorSubject, throwError } from 'rxjs';
import { switchMap, filter, take, map, catchError } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';


@Injectable({ providedIn: 'root' })
export class TokenInterceptor implements HttpInterceptor {


  private refreshTokenInProgress = false;

  private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(
    null
  );

  constructor(
    private authSRV: AuthService
  ) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>
  {

    if (request.url.includes("oauth")) {
      return next.handle(request);
    }

    if ( this.authSRV.getToken() != null && !this.authSRV.isTokenExpirado() ) {

      return next.handle(this.addAuthenticationToken(request))

    }

    return next.handle(this.addAuthenticationToken(request)).pipe(
        catchError((error: HttpErrorResponse) => {

          if (
            request.url.includes("oauth") ||
            request.url.includes("login")
          ) {
            if (request.url.includes("refreshtoken")) {
              this.authSRV.logout();
            }

            return throwError(error);
          }

          if (error.status !== 401) {
            return throwError(error);
          }

          if (this.refreshTokenInProgress) {

            return this.refreshTokenSubject.pipe(
              filter(result => result !== null),
              take(1),
              switchMap(() => {
                  return next.handle(this.addAuthenticationToken(request))
              })
            );

          } else {

            this.refreshTokenInProgress = true;

            this.refreshTokenSubject.next(null);

            return this.authSRV.refreshToken().pipe(

              switchMap((res: any) => {

                this.refreshTokenInProgress = false;
                this.refreshTokenSubject.next(res.access_token);

                this.authSRV.guardarToken(res.access_token)

                return next.handle(this.addAuthenticationToken(request));

              }),

              catchError((err: any) => {

                this.refreshTokenInProgress = false;

                this.authSRV.logout();
                return throwError(error);
              })

            )

          }

          return throwError(error);

      })
    )as Observable<HttpEvent<any>>;


  }

  addAuthenticationToken(request: HttpRequest<any>) {

    // Get access token from Local Storage
    const accessToken = this.authSRV.getToken();

    // If access token is null this means that user is not logged in
    // And we return the original request
    if (!accessToken) {
        return request;
    }

    const authReq = request.clone({
      headers: request.headers.set('Authorization', 'Bearer ' + accessToken)
    });

    // We clone the request, because the original request is immutable
    return authReq;
  }

}
