import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NavigationInfo } from '../models/NavigationInfo';

@Injectable({
  providedIn: 'root'
})
export class CommonsRestService {

  constructor(private httpClient: HttpClient) { }


  protected getData(url: string, criteria: any): Observable<any> {

    if (criteria) {

      url += '?';

      Object.keys(criteria).forEach(key => {

        if (typeof criteria[key] !== 'object' && criteria[key] && criteria[key] != null && criteria[key] !== '') {
          url += `${key}=${criteria[key]}&`;
        }

      });

    }

    url = url.slice(0, -1);

    return this.httpClient.get<any>(url);

  }

  protected getListData(url: string, navInfo: NavigationInfo, criteria: any): Observable<any> {

    url += '?';

    Object.keys(navInfo).forEach(key => {

      if (navInfo[key as keyof NavigationInfo] != null) {
        url += `${key}=${navInfo[key as keyof NavigationInfo]}&`;
      }

    });

    if (criteria) {

      Object.keys(criteria).forEach(key => {

        if (typeof criteria[key] !== 'object' && criteria[key] && criteria[key] != null && criteria[key] !== '') {
          url += `${key}=${criteria[key]}&`;
        }

      });

    }

    url = url.slice(0, -1);

    return this.httpClient.get<any>(url);
  }

}
