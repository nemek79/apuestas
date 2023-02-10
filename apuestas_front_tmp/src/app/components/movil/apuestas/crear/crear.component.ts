import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Apuesta } from 'src/app/models/apuesta';
import { DatePipe } from '@angular/common';
import { ApuestasService } from 'src/app/services/apuestas.service';

@Component({
  selector: 'app-crear',
  templateUrl: './crear.component.html',
  styleUrls: ['./crear.component.scss']
})
export class CrearComponent implements OnInit {

  public formApuesta: FormGroup;
  public isAuthLoading = false;
  public statusSubmited = false;

  private apuesta: Apuesta = new Apuesta();

  constructor(
    private formBuilder: FormBuilder,
    private route: Router,
    private toastr: ToastrService,
    public datepipe: DatePipe,
    private apuestasSRV: ApuestasService
  ) { }

  ngOnInit(): void {

    this.cargarFormulario();

  }

  public crearApuesta(): void {

    console.log('CrearApuesta')

    this.statusSubmited = true;

    if (this.formApuesta.valid) {

      this.isAuthLoading = true;

      this.apuesta.apuesta = this.formApuesta.controls['apuestaIn'].value;
      this.apuesta.bruto = 0;
      this.apuesta.cantidadApostada = this.formApuesta.controls['cantidadApostadaIn'].value;
      this.apuesta.comentario = this.formApuesta.controls['comentarioIn'].value;
      this.apuesta.cuota = this.formApuesta.controls['cuotaIn'].value;
      this.apuesta.deporte.id = this.formApuesta.controls['deporteIn'].value;
      this.apuesta.encuentro = this.formApuesta.controls['encuentroIn'].value;
      this.apuesta.estado.id = 1;
      this.apuesta.evento = this.formApuesta.controls['eventoIn'].value;
      this.apuesta.fechaAlta = this.datepipe.transform((new Date), 'yyyy-MM-dd');
      this.apuesta.fechaEvento = this.formApuesta.controls['fechaEventoIn'].value;
      this.apuesta.id = null;
      this.apuesta.live = (this.formApuesta.value['liveIn']?true:false);
      this.apuesta.modificado = (this.formApuesta.value['modificadoIn']?true:false);
      this.apuesta.reto = (this.formApuesta.value['retoIn']?true:false);
      this.apuesta.stake = this.formApuesta.controls['stakeIn'].value;
      this.apuesta.tipoApuesta.id = this.formApuesta.controls['tipoIn'].value;
      this.apuesta.tipster.id = this.formApuesta.controls['tipsterIn'].value;

      this.apuestasSRV.saveApuesta(this.apuesta).subscribe(
        resp => {
          if (resp.code === 0) {
            this.toastr.info('Info','La apuesta se ha creado con éxito!', {
              timeOut: 5000,
            });
            this.route.navigate(['/movil/dashboard']);
          } else {
            this.toastr.error('Error!', 'La acción no se ha podido realizar.', {
              timeOut: 5000,
            })
          }
        },
        err => {
          console.log('ERROR')
          console.log(err)

          this.toastr.error('Error!', 'Se ha producido un error en la conexión.', {
            timeOut: 5000,
          });
        }
      );

    }

  }

  private cargarFormulario() {

    const cuotasRegEx = /^\d*(\.)?(\d{0,3})?$/;
    const stakeRegEx = /^\d*(\.)?(\d{0,1})?$/;

    this.formApuesta = this.formBuilder.group({
      fechaEventoIn: [this.datepipe.transform((new Date), 'yyyy-MM-dd'), Validators.required],
      eventoIn: ['', [Validators.required]],
      apuestaIn: ['', Validators.required],
      encuentroIn: ['', Validators.required],
      comentarioIn: [''],
      tipsterIn:  ['1', Validators.required],
      tipoIn: ['1', Validators.required],
      deporteIn: ['1', Validators.required],
      stakeIn: ['', [Validators.required,Validators.pattern(stakeRegEx)]],
      cuotaIn: ['', [Validators.required,Validators.pattern(cuotasRegEx)]],
      cantidadApostadaIn: ['', [Validators.required,Validators.pattern(cuotasRegEx)]],
      liveIn: [''],
      retoIn: [''],
      modificadoIn: ['']
    });

  }

  get f(): { [key: string]: AbstractControl } {
    return this.formApuesta.controls;
  }

}
