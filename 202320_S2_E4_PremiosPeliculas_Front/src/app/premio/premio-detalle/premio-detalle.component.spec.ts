/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { PremioDetalleComponent } from './premio-detalle.component';
import { OrganizacionDTO } from 'src/app/organizacion/organizacion';
import { faker } from '@faker-js/faker';
import { PremiacionDTO } from 'src/app/premiacion/premiacion';
import { PremioDetailDTO } from '../premioDetail';
import { PremiacionDetail } from 'src/app/premiacion/premiacionDetail';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { PeliculaDTO } from 'src/app/pelicula/pelicula';
import { PersonaDTO } from 'src/app/persona/personaDTO';
import { GanadorDTO } from 'src/app/ganador/ganador';

describe('PremioDetalleComponent', () => {
  let component: PremioDetalleComponent;
  let fixture: ComponentFixture<PremioDetalleComponent>;
  let debug: DebugElement;


  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule],
      declarations: [ PremioDetalleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PremioDetalleComponent);
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

    component.premioDetalle = new PremioDetailDTO(
       faker.number.int(),
       faker.date.past(),
       faker.lorem.sentence(),
       premiacion,
       organizacion,
       []
     );

    fixture.detectChanges();
    debug = fixture.debugElement;

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('debe crear un div para cada detalle del premio', () => {
    const detailItems = debug.queryAll(By.css('.premio-detail-item'));
    expect(detailItems.length).toBeGreaterThan(0);
  });

  it('debe tener un enlace para cada reseña', () => {
    const reseniasLinks = debug.queryAll(By.css('.premio-resenia-link'));
    expect(reseniasLinks.length).toBe(component.premioDetalle.resenias.length);
  });

  it('debe mostrar correctamente el país del premio', () => {
    const paisElement = debug.query(By.css('.premio-detail-item:nth-child(4) .premio-value'));
    expect(paisElement.nativeElement.textContent).toContain(component.premioDetalle.pais);
  });

  it('debe tener un header para la organización', () => {
    const organizacionHeader = debug.query(By.css('.premio-detail-item:nth-child(5) .premio-label'));
    expect(organizacionHeader.nativeElement.textContent).toContain('Organización:');
  });

});
