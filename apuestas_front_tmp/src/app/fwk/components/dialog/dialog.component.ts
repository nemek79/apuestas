import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';
import { EventResponse } from '../../models/event-response';

declare var window: any;

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.scss']
})
export class DialogComponent implements OnInit {

  @Input() configuration: any = {};
  @Input() isOpen: boolean = false;

  @Output() evento = new EventEmitter<EventResponse>();

  public messageModal: any;

  public dialogId: string = 'myDialogo';
  public title: string = 'Diálogo';
  public texto: string = 'Texto del diálogo';
  public tipo: string = 'danger'; // 'informativo','aviso','error'

  constructor() { }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges) {

    if (changes.isOpen.currentValue) {

      this.init();

      let element: any = document.getElementById('myDialogo');

      if (!element) return;

      this.messageModal = new window.bootstrap.Modal(
        element
      );

      element.addEventListener('hidden.bs.modal', () => {
        this.cerrarModal();
      })

      this.messageModal.show();

    }

  }

  public cerrarModal():void {

    let response: EventResponse = new EventResponse();

    response.type = 'closeModal';
    response.info = null;

    this.evento.emit(response);

  }

  private init(): void {

    if (this.configuration.id) {
      this.dialogId = this.configuration.id;
    }

    if (this.configuration.title) {
      this.title = this.configuration.title;
    }

    if (this.configuration.text) {
      this.texto = this.configuration.text;
    }

    if (this.configuration.type) {

      switch (this.configuration.type) {
        case 'informativo':
          this.tipo = 'secondary';
          break;
        case 'aviso':
          this.tipo = 'warning';
          break;
        case 'error':
          this.tipo = 'danger';
          break;
        default:
          this.tipo = 'dark';
          break;
      }

      this.tipo = this.configuration.type;
    }
  }

}
