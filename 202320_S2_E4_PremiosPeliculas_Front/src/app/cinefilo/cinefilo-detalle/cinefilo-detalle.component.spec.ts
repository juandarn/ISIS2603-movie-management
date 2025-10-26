/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';


import { CinefiloDetalleComponent } from './cinefilo-detalle.component';
import { CinefiloDetailDTO } from '../cinefiloDetailDTO';
import { faker } from '@faker-js/faker';
import { CinefiloService } from '../cinefilo.service';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

describe('CinefiloDetalleComponent', () => {
  let component: CinefiloDetalleComponent;
  let fixture: ComponentFixture<CinefiloDetalleComponent>;
  let debug:DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CinefiloDetalleComponent ],
      imports:[HttpClientModule, RouterTestingModule],
      providers:[CinefiloService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CinefiloDetalleComponent);
    component = fixture.componentInstance;

    component.cinefiloDetailDTO = new CinefiloDetailDTO(
      faker.number.int(),
      faker.person.fullName(),
      faker.number.int(),
      []
      );
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
