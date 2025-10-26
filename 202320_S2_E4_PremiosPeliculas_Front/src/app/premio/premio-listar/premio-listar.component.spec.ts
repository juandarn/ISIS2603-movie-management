/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { faker } from '@faker-js/faker';

import { PremioListarComponent } from './premio-listar.component';
import { PremioService } from '../premio.service';
import { PremioDetailDTO } from '../premioDetail';
import { PremiacionDTO } from 'src/app/premiacion/premiacion';
import { OrganizacionDTO } from 'src/app/organizacion/organizacion';
import { PremiacionDetail } from 'src/app/premiacion/premiacionDetail';
import { RouterTestingModule } from '@angular/router/testing';
import { PeliculaDTO } from 'src/app/pelicula/pelicula';
import { PersonaDTO } from 'src/app/persona/personaDTO';
import { GanadorDTO } from 'src/app/ganador/ganador';

describe('PremioListarComponent', () => {
  let component: PremioListarComponent;
  let fixture: ComponentFixture<PremioListarComponent>;
  let debug: DebugElement;
  let debugPremio: PremioDetailDTO;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule],
      declarations: [ PremioListarComponent ],
      providers: [PremioService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PremioListarComponent);
    component = fixture.componentInstance;

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
    );

    let premiacion = new PremiacionDetail(
      faker.number.int(),
      faker.lorem.words(5),
      faker.lorem.words(10),
      pelicula,
      [],
      ganador,
      [],
      []
    );

    let organizacion = new OrganizacionDTO(
      faker.number.int(),
      faker.lorem.words(2),
    );

    let testPremios : Array<PremioDetailDTO> = [];

    for(let i = 0; i<10; i++) {
      const premio = new PremioDetailDTO(
        faker.number.int(),
        faker.date.past(),
        faker.lorem.sentence(),
        premiacion,
        organizacion,
        []
      );
      component.premios.push(premio);
    }

    fixture.detectChanges();
    debug = fixture.debugElement;


  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have 10 <tbody tr> elements', () => {
   expect(debug.queryAll(By.css('tbody tr'))).toHaveSize(10)
  });
});
