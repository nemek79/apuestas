import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { environment } from 'src/environments/environment';
import { NavigationInfo } from '../fwk/models/NavigationInfo';
// import { CommonsRestService } from '../fwk/services/commonsRest.service';
import { Apuesta } from '../models/apuesta';
import { CommonsRestObsService } from '../fwk/services/commonsRestObs.service';

@Injectable({
  providedIn: 'root'
})
export class ApuestasService extends CommonsRestObsService {

  constructor(
    private http: HttpClient
  ) {
    super(http);
  }

  public saveApuesta(apuesta: Apuesta): Observable<any> {

    const urlEndpoint = environment.urlBack + environment.urlApuestas;

    return this.http.post<any>(urlEndpoint,apuesta);

  }

  public getApuestas(navInfo: NavigationInfo, criteria: any): Observable<any> {

    const urlEndpoint = environment.urlBack + environment.urlApuestas;

    /*
    const resp = await this.getListData(urlEndpoint,navInfo,criteria);

    return resp;
    */

    return this.getListData(urlEndpoint,navInfo,criteria);
  }

  public updateEstadoApuesta(apuesta_id: number, estado_id: number): Observable<any> {

    const urlEndpoint = environment.urlBack + environment.urlApuestas + '/estado/' + apuesta_id + '?estado=' + estado_id;

    return this.http.put<any>(urlEndpoint,null);

  }


}
