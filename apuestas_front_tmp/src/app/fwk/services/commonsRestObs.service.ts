import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NavigationInfo } from '../models/NavigationInfo';

@Injectable({providedIn: 'root'})
export class CommonsRestObsService {

  constructor(private httpClient: HttpClient) { }


  protected getData(url: string): Observable<any> {

    return this.httpClient.get<any>(url);

  }

  protected getListData(url: string, navInfo: NavigationInfo, criteria: any): Observable<any> {

    url += '?';

    Object.keys(navInfo).forEach(key => {

      if (navInfo[key] != null) {
        url += `${key}=${navInfo[key]}&`;
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
