import { Component, OnInit } from '@angular/core';
import { NavigationInfo } from 'src/app/fwk/models/NavigationInfo';
import { Apuesta } from 'src/app/models/apuesta';
import { ApuestaRequest } from 'src/app/models/request/apuesta-request';
import { ApuestasService } from 'src/app/services/apuestas.service';

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

  constructor(
    private apuestasSRV: ApuestasService
  ) { }

  ngOnInit(): void {
    this.cargarApuestas();
  }


  private async cargarApuestas() {

    this.criteria = new ApuestaRequest();

    this.criteria.estadoId = 1; // Estado pendiente

    const resultado:any = await this.apuestasSRV.getApuestas(this.navigation,this.criteria);

    if (resultado.code == 0) {

      this.apuestasList = resultado.data;
      this.numTotalApuestas = resultado.total;

      console.log(this.apuestasList);
      console.log(this.numTotalApuestas);

    } else {
      console.error('Error al obtener la lista de apuestas.')
    }


  }



}
