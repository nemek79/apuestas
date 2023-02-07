import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { environment } from 'src/environments/environment';
import { NavigationInfo } from '../fwk/models/NavigationInfo';
import { CommonsRestService } from '../fwk/services/commonsRest.service';
import { Apuesta } from '../models/apuesta';

@Injectable({
  providedIn: 'root'
})
export class ApuestasService extends CommonsRestService {

  constructor(
    private http: HttpClient
  ) {
    super(http);
  }

  public async saveApuesta(apuesta: Apuesta) {

    const urlEndpoint = environment.urlBack + environment.urlApuestas;

    const resp = await this.http.post<any>(urlEndpoint,apuesta).toPromise();

    return resp;

  }

  public async getApuestas(navInfo: NavigationInfo, criteria: any) {

    const urlEndpoint = environment.urlBack + environment.urlApuestas;

    const resp = await this.getListData(urlEndpoint,navInfo,criteria);

    return resp;

  }


}
