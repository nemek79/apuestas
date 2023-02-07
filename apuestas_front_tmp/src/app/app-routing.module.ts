import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TestComponent } from './components/test/test.component';
import { DashboardComponent } from './components/movil/dashboard/dashboard.component';
import { AuthGuard } from './fwk/guards/auth.guard';
import { RoleGuard } from './fwk/guards/role.guard';
import { RutaAnterior } from './fwk/guards/ruta-anterior.guard';
import { LoginComponent } from './components/movil/login/login.component';
import { MainFrameComponent } from './components/movil/layout/main-frame/main-frame.component';
import { CrearComponent } from './components/movil/apuestas/crear/crear.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'movil',
    component: MainFrameComponent,
    children: [
      {
        path: 'dashboard',
        component: DashboardComponent,
        canActivate: [AuthGuard, RoleGuard], data: {role: 'ROLE_ADMIN'},
        canDeactivate: [RutaAnterior]
      },
      {
        path: 'apuestas/crear',
        component: CrearComponent,
        canActivate: [AuthGuard, RoleGuard], data: {role: 'ROLE_ADMIN'},
        canDeactivate: [RutaAnterior]
      },
      {
        path: 'test',
        component: TestComponent,
        canDeactivate: [RutaAnterior]
      }
    ]
  },
  {path: '**', redirectTo: '/movil/dashboard'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {relativeLinkResolution: 'legacy'})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
