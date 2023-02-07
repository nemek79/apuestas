import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor(
    private http: HttpClient
  ) {

  }

  public setItem(key: string, value: string): void {

    localStorage.setItem(environment.app + '.' + key, value);

  }

  public getItem(key: string): string {

    return localStorage.getItem(environment.app + '.' + key);

  }

  public removeItem(key: string): void {

    localStorage.removeItem(environment.app + '.' + key);

  }

  public clear(): void {

    localStorage.clear();

  }

  public setItemSession(key: string, value: string): void {

    sessionStorage.setItem(environment.app + '.' + key, value);

  }

  public getItemSession(key: string): string {

    return sessionStorage.getItem(environment.app + '.' + key);

  }

  public removeItemSession(key: string): void {

    sessionStorage.removeItem(environment.app + '.' + key);

  }

  public clearSession(): void {

    sessionStorage.clear();

  }

}
