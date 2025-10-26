/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { PremiacionDetailComponent } from './premiacion-detail.component';
import { PremiacionDetail } from '../premiacionDetail';
import { faker } from '@faker-js/faker';
import { OrganizacionDTO } from 'src/app/organizacion/organizacion';
import { PremioDTO } from 'src/app/premio/premio';
import { PeliculaDTO } from 'src/app/pelicula/pelicula';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { PersonaDTO } from 'src/app/persona/personaDTO';
import { GanadorDTO } from 'src/app/ganador/ganador';
import { PremiacionDTO } from '../premiacion';

describe('PremiacionDetailComponent', () => {
  let component: PremiacionDetailComponent;
  let fixture: ComponentFixture<PremiacionDetailComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule , RouterTestingModule],
      declarations: [ PremiacionDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PremiacionDetailComponent);
    component = fixture.componentInstance;

    const organizacion = new OrganizacionDTO(
      faker.number.int(),
      faker.lorem.words(2),
    );

    let pelicula = new PeliculaDTO(
      faker.number.int(),
      faker.lorem.sentence(),
      faker.number.int(),
      faker.location.country(),
      faker.lorem.word(),
      faker.date.past(),
      faker.lorem.word(),
      faker.lorem.word()
    );

    let premiacion1 = new PremiacionDTO(
      faker.number.int(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      pelicula
    );

    let persona = new PersonaDTO(
      faker.number.int(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      faker.date.anytime(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      faker.lorem.sentence()
    );

    let ganador = new GanadorDTO(
      faker.number.int(),
      persona,
      premiacion1
    )

    const premiacion = new PremiacionDetail(
      faker.number.int(),
      faker.lorem.words(5),
      faker.lorem.words(10),
      pelicula,
      [],
      ganador,
      [],
      []
    );

    const premios: PremioDTO[] = [];
    for (let i = 0; i < 3; i++) {
      const premio = new PremioDTO (
        faker.number.int(),
        faker.date.past(),
        faker.lorem.sentence(),
        premiacion,
        organizacion
      );
      premios.push(premio);
    }

    const peliculas: PeliculaDTO[] = [];
    for (let i = 0; i < 3; i++) {
      const pelicula = new PeliculaDTO (
        faker.number.int(),
        faker.lorem.sentence(),
        faker.number.int(),
        faker.lorem.sentence(),
        faker.lorem.sentence(),
        faker.date.past(),
        faker.lorem.sentence(),
        faker.lorem.sentence(),
      );
      peliculas.push(pelicula);
    }


    component.premiacionDetail = new PremiacionDetail(
      faker.number.int(),
      faker.lorem.words(5),
      faker.lorem.words(10),
      pelicula,
      premios,
      ganador,
      [],
      peliculas
    );

    fixture.detectChanges();
    debug = fixture.debugElement;

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

    it('should display the category in the card title', () => {
      const cardTitle: HTMLElement = debug.query(By.css('.card-title')).nativeElement;
      expect(cardTitle.textContent).toContain(component.premiacionDetail.categoria);
    });


    it('should display the history in the card text', () => {
      const cardText: HTMLElement = debug.query(By.css('.card-text')).nativeElement;
      expect(cardText.textContent).toContain(component.premiacionDetail.historia);
    });




    


});
