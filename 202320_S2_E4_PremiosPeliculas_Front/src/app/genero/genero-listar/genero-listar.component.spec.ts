/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { GeneroListarComponent } from './genero-listar.component';
import { HttpClientModule } from '@angular/common/http';
import { GeneroDetail } from '../generoDetail';
import { GeneroService } from './genero.service';
import { faker } from '@faker-js/faker';

describe('GeneroListarComponent', () => {
  let component: GeneroListarComponent;
  let fixture: ComponentFixture<GeneroListarComponent>;
  let debug: DebugElement;
  let debugGeneroDetail: GeneroDetail;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      declarations: [ GeneroListarComponent ],
      providers: [GeneroService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GeneroListarComponent);
    component = fixture.componentInstance;


  for(let i = 0; i < 10; i++) {
    const genero = new GeneroDetail(
      faker.number.int(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      [],
    );
    component.generos.push(genero);
    debug = fixture.debugElement;
  }
  fixture.detectChanges();
});

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should have 10 <tbody tr> elements', () => {
    expect(debug.queryAll(By.css('tbody tr'))).toHaveSize(10)
   });
});
