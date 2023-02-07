import { Injectable } from '@angular/core';
import { Router, CanDeactivate } from '@angular/router';
import { StorageService } from '../services/storage.service';
import { Observable } from 'rxjs';

@Injectable()
export class RutaAnterior implements CanDeactivate<any> {
  constructor(
    private router: Router,
    private config: StorageService
  ) { }
  canDeactivate(component: any): Observable<boolean> | boolean {
    this.config.setItem('rutaAnterior', this.router.url);
    return true;
  }
}
