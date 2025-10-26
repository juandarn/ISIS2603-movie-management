/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { faker } from '@faker-js/faker';
import { PeliculaDetalleComponent } from './pelicula-detalle.component';
import { PremiacionDTO } from 'src/app/premiacion/premiacion';
import { GeneroDTO } from 'src/app/genero/genero';
import { PeliculaDetail } from '../peliculaDetail';
import { PersonaDTO } from 'src/app/persona/personaDTO';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { PeliculaDTO } from '../pelicula';

describe('PeliculaDetalleComponent', () => {
  let component: PeliculaDetalleComponent;
  let fixture: ComponentFixture<PeliculaDetalleComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports:[RouterTestingModule, HttpClientModule],
      declarations: [ PeliculaDetalleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PeliculaDetalleComponent);
    component = fixture.componentInstance;

    let pelicula1 = new PeliculaDTO(
      faker.number.int(),
      faker.lorem.sentence(),
      faker.number.int(),
      faker.location.country(),
      faker.lorem.word(),
      faker.date.past(),
      faker.lorem.word(),
      faker.lorem.word()
    );

    let premiaciones: Array<PremiacionDTO> = [ ];
    for (let i = 0; i < 3; i++) {
      let premiacion = new PremiacionDTO(
        faker.number.int(),
        faker.lorem.words(),
        faker.lorem.words(),
        pelicula1
      );
    premiaciones.push(premiacion);
    }

    let personas: Array<PersonaDTO> = [ ];
    
    for (let i = 0; i < 3; i++) {
      let persona = new PersonaDTO(
        faker.number.int(),
        faker.lorem.words(),
        faker.lorem.words(),
        faker.date.past(),
        faker.lorem.words(),
        faker.lorem.words(),
        faker.lorem.sentence()
      );
    personas.push(persona);
    }

    let generos: Array<GeneroDTO> = [ ];
    for (let i = 0; i < 3; i++) {
      let genero = new GeneroDTO(
        faker.number.int(),
        faker.lorem.words(),
        faker.lorem.words()
      );
    generos.push(genero); 
    }

    let pelicula = new PeliculaDetail(
      faker.number.int(),
      faker.lorem.words(),
      faker.number.int(),
      faker.lorem.words(),
      faker.lorem.words(),
      faker.date.past(),
      faker.lorem.words(),
      faker.lorem.words(),
      premiaciones,
      [],
      personas,
      generos,
      [],
      []
    );
    component.peliculaDetail = pelicula;

    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have a p.h3.p-3 element with peliculaDetail.name', () => {
    const element: HTMLElement = debug.query(By.css('p.h3.p-3')).nativeElement;
    console.log(element.textContent);
    expect(element.textContent).toContain(component.peliculaDetail.nombre);
  });

  it('should have an img element with src= bookDetail.image', () => {
    expect(debug.query(By.css('img')).attributes['src']).toEqual(
      component.peliculaDetail.imagen
    );
  });
 
  it('should have an img element with alt= bookDetail.name', () => {
    expect(debug.query(By.css('img')).attributes['alt']).toEqual(
      component.peliculaDetail.nombre
    );
  }); 

  it('should have 1 <dd> elements', () => {
    expect(debug.queryAll(By.css('dd.caption'))).toHaveSize(1)
  });

  it('should have one dd tag for component.peliculaDetail.nombre', () => {
    const allDt : DebugElement[] = debug.queryAll(By.css('dt'));
    const node = allDt.find((value) => {
      return value.nativeElement.textContent == 'Nombre';
    });
    expect(node?.nativeElement.nextSibling.textContent).toContain(component.peliculaDetail.nombre);
  });

  it('should have one dd tag for component.peliculaDetail.pais', () => {
    const allDt : DebugElement[] = debug.queryAll(By.css('dt'));
    const node = allDt.find((value) => {
      return value.nativeElement.textContent == 'Pais';
    });
    expect(node?.nativeElement.nextSibling.textContent).toContain(component.peliculaDetail.pais);
  });

  it('should have one dd tag for component.peliculaDetail.duracion', () => {
    const allDt : DebugElement[] = debug.queryAll(By.css('dt'));
    const node = allDt.find((value) => {
      return value.nativeElement.textContent == 'Duración';
    });
    expect(node?.nativeElement.nextSibling.textContent).toContain(component.peliculaDetail.duracion);
  });

  it('should have one dd tag for component.peliculaDetail.idiomaOriginal', () => {
    const allDt : DebugElement[] = debug.queryAll(By.css('dt'));
    const node = allDt.find((value) => {
      return value.nativeElement.textContent == 'Idioma original';
    });
    expect(node?.nativeElement.nextSibling.textContent).toContain(component.peliculaDetail.idiomaOriginal);
  });

  it('should have one dd tag for component.peliculaDetail.fechaEstreno', () => {
    const allDt : DebugElement[] = debug.queryAll(By.css('dt'));
    const node = allDt.find((value) => {
      return value.nativeElement.textContent == 'Fecha estreno';
    });
    expect(node?.nativeElement.nextSibling.textContent).toContain(component.peliculaDetail.fechaEstreno);
  });

  it('should have one dd tag for component.peliculaDetail.LinkTrailer', () => {
    const allDt : DebugElement[] = debug.queryAll(By.css('dt'));
    const node = allDt.find((value) => {
      return value.nativeElement.textContent == 'Link Trailer';
    });
    const linkElement: HTMLElement = node?.nativeElement.nextSibling;
    expect(linkElement.textContent).toContain(component.peliculaDetail.linkTrailer);
  });

  
  
  

  

});
