/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { PersonaDetalleComponent } from './persona-detalle.component';
import { HttpClientModule } from '@angular/common/http';
import { PersonaDetailDTO } from '../personaDetailDTO';
import { faker } from '@faker-js/faker';
import { RouterTestingModule } from '@angular/router/testing';
import { ActivatedRoute } from '@angular/router';

describe('PersonaDetalleComponent', () => {
  let component: PersonaDetalleComponent;
  let fixture: ComponentFixture<PersonaDetalleComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule],
      declarations: [ PersonaDetalleComponent ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: { paramMap: { get: (param: string) => '1' } }
          }
        }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonaDetalleComponent);
    component = fixture.componentInstance;

    component.personaDetailDTO = new PersonaDetailDTO(
      faker.number.int(),
      faker.person.fullName(),
      faker.lorem.word(),
      faker.date.birthdate(),
      faker.person.bio(),
      "Persona",
      [],
      [],
      [],
      faker.lorem.sentence()
      );
    fixture.detectChanges();
    debug = fixture.debugElement;
    


  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have a card with person data', () => {
    let datos = debug.query(By.css('.card.DataPerson'));
    expect(datos.query(By.css('img')).attributes["src"]).toContain(component.personaDetailDTO.imagen);
    expect(datos.query(By.css('h2')).nativeElement.textContent).toMatch(component.personaDetailDTO.nombre);
    expect(datos.queryAll(By.css('p'))[0].nativeElement.textContent).toMatch(component.personaDetailDTO.biografia);
   });
});
