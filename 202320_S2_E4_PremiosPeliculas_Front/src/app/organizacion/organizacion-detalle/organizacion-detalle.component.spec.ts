import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { OrganizacionDetalleComponent } from './organizacion-detalle.component';
import { PremioDetailDTO } from 'src/app/premio/premioDetail';
import { faker } from '@faker-js/faker';
import { PremiacionDetail } from 'src/app/premiacion/premiacionDetail';
import { OrganizacionDTO } from '../organizacion';
import { OrganizacionDetail } from '../organizacionDetail';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';


describe('OrganizacionDetalleComponent', () => {
  let component: OrganizacionDetalleComponent;
  let fixture: ComponentFixture<OrganizacionDetalleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports :[HttpClientModule, RouterTestingModule],
      declarations: [ OrganizacionDetalleComponent ]
    })
    .compileComponents();
  }));



  beforeEach(() => {
    fixture = TestBed.createComponent(OrganizacionDetalleComponent);
    component = fixture.componentInstance;


   



    component.organizacionDetail = new OrganizacionDetail(
      faker.number.int(),
      faker.lorem.sentence(),
      [],
    );

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
