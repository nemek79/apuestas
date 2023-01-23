import { Injectable } from '@angular/core';
import { Usuario } from '../models/usuario';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { StorageService } from '../services/storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private usuario: Usuario;
  private token: string;
  private refresh: string;
  private time: number;

  constructor(
    private http: HttpClient,
    private storageSRV: StorageService
  ) {

  }

  public async login(usuario: Usuario) {

    const urlEndpoint = environment.urlEndPointAuth + '/oauth/token';
    const credenciales = btoa(environment.client + ':' + environment.clientPass);

    const httpHeaders = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
      Authorization: 'Basic ' + credenciales
    });

    let params = new URLSearchParams();

    params.set('grant_type', 'password');
    params.set('username', usuario.username);
    params.set('password', usuario.password);

    const resp = await this.http.post<any>(urlEndpoint, params.toString(), {headers: httpHeaders}).toPromise();

    return resp;

  }

  public getToken(): string {

    if (this.token != null) {
      return this.token;
    } else if (this.token == null && this.storageSRV.getItem('token')) {
      this.token = this.storageSRV.getItem('token');
      return this.token;
    }

    return null;

  }

  public getRefreshToken(): string {

    if (this.refresh != null) {
      return this.refresh;
    } else if (this.refresh == null && this.storageSRV.getItem('refresh')) {
      this.refresh = this.storageSRV.getItem('refresh');
      return this.refresh;
    }

    return null;

  }

  public setTokenToNull(): void {

    this.token = null;
    this.storageSRV.removeItem('token');

  }

  guardarUsuario(accessToken: string): void {

    const payload = this.obtenerDatosToken(accessToken);

    this.usuario = new Usuario();

    this.usuario.roles = payload.authorities;
    this.usuario.username = payload.user_name;

    // Guardar el usuario en el session storage
    this.storageSRV.setItem('usuario', JSON.stringify(this.usuario));
  }

  guardarToken(accessToken: string): void {

      this.token = accessToken;
      this.storageSRV.setItem('token', accessToken);

  }

  guardarRemember(refreshToken: string, time: number): void {

    this.refresh = refreshToken;
    this.time = time;

    this.storageSRV.setItem('refresh', refreshToken);
    this.storageSRV.setItem('time', time.toString());

  }

  obtenerDatosToken(accessToken: string): any {

    if (accessToken != null) {
      return JSON.parse(atob(accessToken.split('.')[1]));
    }

    return null;
  }

  hasRole(role: string): boolean {

    if (this.getUsuario().roles.includes(role)) {
      return true;
    }

    return false;

  }

  logout(): void {

      this.token = null;
      this.usuario = null;
      this.refresh = null;
      this.time = null;

      this.storageSRV.clear();
      this.storageSRV.clearSession();

  }

  public getUsuario(): Usuario {

    if (this.usuario != null) {
      return this.usuario;
    } else if (this.usuario == null && this.storageSRV.getItem('usuario')) {
      this.usuario = JSON.parse(this.storageSRV.getItem('usuario')) as Usuario;
      return this.usuario;
    }

    return new Usuario();
  }

  public isRemembered(): boolean {

    if (this.refresh != null) {
      return true;
    }

    return false;
  }

  public refreshToken(): Observable<any> {

    const urlEndpoint = environment.urlEndPointAuth + '/oauth/token';
    const credenciales = btoa(environment.client + ':' + environment.clientPass);

    const httpHeaders = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
      Authorization: 'Basic ' + credenciales
    });

    const params = new URLSearchParams();

    params.set('grant_type', 'refresh_token');
    params.set('refresh_token', this.refresh);

    const tmp = this.http.post<any>(urlEndpoint, params.toString(), {headers: httpHeaders});

    return tmp;

  }

  isAuthenticated(): boolean {

    const payload = this.obtenerDatosToken(this.getToken());

    if (payload != null && payload.user_name && payload.user_name.length > 0) {

      return true;
    }

    return false;
  }

  public isTokenExpirado(): boolean {

    let token = this.getToken();

    let payload = this.obtenerDatosToken(token);

    let now = new Date().getTime() / 1000; // Fecha actual en segundos

    if (payload.exp < now)  {
      return true;
    }

    return false;
  }

}
