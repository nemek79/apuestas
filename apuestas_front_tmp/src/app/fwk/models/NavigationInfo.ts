export class NavigationInfo {

  first: number;
  rows: number;
  sortField: string;
  sortOrder: number;
  sortFieldDefault: string[];
  sortOrderDefault: number[];

  constructor(){

    this.first = 0;
    this.rows = 20;
    this.sortField = '';
    this.sortOrder = 0;
    this.sortFieldDefault = [];
    this.sortOrderDefault = [];

  }

}
