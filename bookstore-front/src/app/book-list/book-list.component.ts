import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'bs-book-list',
  templateUrl: './book-list.component.html',
  styles: []
})
export class BookListComponent implements OnInit {

  private nbBooks: number = 2;
  books = [
    {
      id: "1",
      title: "dummy title 1",
      description: "dummy description 1",
    },
    {
      id: "2",
      title: "dummy title 1",
      description: "dummy description 1",
    },
    {
      id: "3",
      title: "dummy title 1",
      description: "dummy description 1",
    }
  ]
  constructor() { }

  ngOnInit() {
  }

}
