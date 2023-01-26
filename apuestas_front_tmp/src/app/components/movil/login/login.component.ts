import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Usuario } from 'src/app/fwk/models/usuario';
import { AuthService } from 'src/app/fwk/services/auth.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  public formLogin: FormGroup;
  public isAuthLoading = false;
  public statusSubmited = false;

  private usuario: Usuario = new Usuario();

  constructor(
    private formBuilder: FormBuilder,
    private authSRV: AuthService,
    private route: Router,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {

    this.cargarFormulario();

  }

  public async login(): Promise<void> {

    if (this.formLogin.valid) {

      this.isAuthLoading = true;

      this.usuario.username = this.formLogin.controls['userIn'].value;
      this.usuario.password = this.formLogin.controls['passwordIn'].value;

      try {

        const resp = await this.authSRV.login(this.usuario);

        console.log(resp)

        this.authSRV.guardarUsuario(resp.access_token);
        this.authSRV.guardarToken(resp.access_token);

      } catch(err:any) {

        if (err.status === 0) {

          this.toastr.error('Conexión rechazada.')

        } else if (err.status === 400 && err.error.error === 'invalid_grant') {

          this.toastr.warning('El usuario o la contraseña no son correctos.')

        } else if (err.status === 401 && err.error.error === 'unauthorized') {

          this.toastr.warning('No posee autorización para acceder a este recurso.')

        } else {

          this.toastr.error('Error desconocido: ' + err.status)

        }

      }

    }

  }

  private cargarFormulario() {
    this.formLogin = this.formBuilder.group({
      userIn: ['', Validators.required],
      passwordIn: ['', [Validators.required, Validators.minLength(5), this.validatePassword]]
    });
  }

  private validatePassword(control: AbstractControl) {

    const password = control.value;

    let error = null;

    /*
    if (!password.includes('$')) {
      error = { ...error, dollar: 'needs a dollar symbol' };
    }
    if (!parseFloat(password[0])) {
      error = { ...error, number: 'must start with a number' };
    }
    */

    return error;
  }

  get f() {

    if (this.formLogin != null) {

      return this.formLogin.controls;

    }

    return null;
  }

}
