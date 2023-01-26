import { HttpLoaderService } from './fwk/services/http-loader.service';
import { AuthService } from './fwk/services/auth.service';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import localeEs from '@angular/common/locales/es';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TestComponent } from './components/test/test.component';
import { HeaderComponent } from './components/movil/layout/header/header.component';
import { DashboardComponent } from './components/movil/dashboard/dashboard.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpInterceptorService } from './fwk/services/http-interceptor.service';
import { CommonsRestService } from './fwk/services/commonsRest.service';
import { StorageService } from './fwk/services/storage.service';
import { TokenInterceptor } from './fwk/interceptors/token.interceptor';
import { registerLocaleData } from '@angular/common';
import { LoginComponent } from './components/movil/login/login.component';
import { MainFrameComponent } from './components/movil/layout/main-frame/main-frame.component';
import { ReactiveFormsModule } from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ToastrModule} from 'ngx-toastr';

registerLocaleData(localeEs, 'es-ES');

@NgModule({
  declarations: [
    AppComponent,
    TestComponent,
    HeaderComponent,
    DashboardComponent,
    LoginComponent,
    MainFrameComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({
      timeOut: 5000,
      preventDuplicates: true
    }),
  ],
  providers: [
    // Framework INI
    AuthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true
    },
    HttpLoaderService,
    CommonsRestService,
    StorageService,
    // Framework FIN
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
