import { OrganizacionService } from './../organizacion.service';
/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { OrganizacionListarComponent } from './organizacion-listar.component';
import { HttpClientModule } from '@angular/common/http';
import { OrganizacionDetail } from '../organizacionDetail';
import { faker } from '@faker-js/faker';

describe('OrganizacionListarComponent', () => {
  let component: OrganizacionListarComponent;
  let fixture: ComponentFixture<OrganizacionListarComponent>;
  let debug: DebugElement;
  let debugGeneroDetail: OrganizacionDetail;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      declarations: [ OrganizacionListarComponent ],
      providers: [OrganizacionService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrganizacionListarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  let testOrganizaciones: Array<OrganizacionDetail>=[];
  for(let i=0;i<10;i++){
    testOrganizaciones[i]= new OrganizacionDetail(
      faker.number.int(),
      faker.lorem.sentence(),
      [],
    )
  }
  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
