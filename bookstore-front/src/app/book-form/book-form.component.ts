import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'bs-book-form',
  templateUrl: './book-form.component.html',
  styles: []
})
export class BookFormComponent implements OnInit {
  book = {
    title: " dummy title",
    description: "dummmy description",
    unitCost:"123",
    isbn:"123-456-789",
    nbOfPages:"345",
    language:" English",
    imageURL:""
  }
  constructor() { }

  ngOnInit() {
  }

}
