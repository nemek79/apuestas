import { Deporte } from "./deporte";
import { EstadoApuesta } from "./estadoApuesta";
import { TipoApuesta } from "./tipoApuesta";
import { Tipster } from "./tipster";

export class Apuesta {

  id: number;
  fechaAlta: string;
  fechaEvento: string;
  tipster: Tipster;
  tipoApuesta: TipoApuesta;
  deporte: Deporte;
  estado: EstadoApuesta;
  encuentro: string;
  comentario: string;
  live: boolean;
  reto: boolean;
  modificado: boolean;
  evento: string;
  apuesta: string;
  stake: number;
  cuota: number;
  cantidadApostada: number;
  bruto: number;

  constructor() {

    this.deporte = new Deporte();
    this.tipoApuesta = new TipoApuesta();
    this.tipster = new Tipster();
    this.estado = new EstadoApuesta();

  }

}
