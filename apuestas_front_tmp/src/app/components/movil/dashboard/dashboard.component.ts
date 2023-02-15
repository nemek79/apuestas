import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';
import { NavigationInfo } from 'src/app/fwk/models/NavigationInfo';
import { Apuesta } from 'src/app/models/apuesta';
import { ApuestaRequest } from 'src/app/models/request/apuesta-request';
import { ApuestasService } from 'src/app/services/apuestas.service';

declare var window: any;

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  public apuestasList = [];
  public numTotalApuestas = 0;

  public navigation: NavigationInfo = new NavigationInfo();
  public criteria: ApuestaRequest;

  public pushModal: any;

  public apuestaId: number;

  constructor(
    private apuestasSRV: ApuestasService,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.apuestaId = -1;
    this.cargarApuestas();
    this.pushModal = new window.bootstrap.Modal(
      document.getElementById('pushCantidadDialog')
    );

  }


  private cargarApuestas() {

    this.criteria = new ApuestaRequest();

    this.apuestasList = [];

    this.criteria.estadoId = 1; // Estado pendiente

    this.apuestasSRV.getApuestas(this.navigation,this.criteria).subscribe(
      resp => {
        if (resp.code === 0) {

          this.apuestasList = resp.data;
          this.numTotalApuestas = resp.total;

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

  public push(apuestaId: number): void {

    let apuesta:Apuesta = this.apuestasList.find(

      apuesta => apuesta.id === apuestaId

    );
      console.log(apuestaId)
      console.log(apuesta)

    this.apuestaId = apuestaId;
    (<HTMLInputElement>document.getElementById('cantidadPushIn')).value = apuesta.cantidadApostada+'';

    this.pushModal.show();

  }

  public cerrarPushCantidadDialog(cantidad: string): void {

    this.apuestasSRV.updateEstadoApuestaPush(this.apuestaId,cantidad).subscribe(
      resp => {
        this.cargarApuestas();
        this.apuestaId = 0;
        this.pushModal.hide();
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

  public actualizarEstadoApuesta(id_apuesta: number, id_estado: number): void {

    /*
      let apuesta = this.apuestasList.find(
        apuesta => apuesta.id = id_apuesta
      );
    */

    this.apuestasSRV.updateEstadoApuesta(id_apuesta,id_estado).subscribe(
      resp => {
        this.cargarApuestas();
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
