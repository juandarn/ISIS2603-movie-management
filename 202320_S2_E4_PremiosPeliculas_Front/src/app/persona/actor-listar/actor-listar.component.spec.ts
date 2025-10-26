/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { ActorListarComponent } from './actor-listar.component';
import { HttpClientModule } from '@angular/common/http';
import { PersonaDTO } from '../personaDTO';
import { faker } from '@faker-js/faker';
import { PersonaService } from '../persona.service';
import { RouterTestingModule } from '@angular/router/testing';
import { PersonaDetailDTO } from '../personaDetailDTO';




describe('ActorListarComponent', () => {
  let component: ActorListarComponent;
  let fixture: ComponentFixture<ActorListarComponent>;
  let debug: DebugElement;
  let debugActorDTO: PersonaDetailDTO

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule],
      declarations: [ ActorListarComponent ],
      providers: [PersonaService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActorListarComponent);
    component = fixture.componentInstance;

    let testActores: Array<PersonaDetailDTO> = [];

    for (let i = 0; i<10;i++){
      testActores[i] = new PersonaDetailDTO(
        faker.number.int(),
        faker.person.fullName(),
        faker.lorem.word(),
        faker.date.birthdate(),
        faker.person.bio(),
        "Actor",
        [],
        [],
        [],
        "https://dummyimage.com/600x400/000/fff"
        );
        component.actores.push(testActores[i]);
    }


    fixture.detectChanges();
    debug = fixture.debugElement;

  });

  it('should create', () => {
    try {
      expect(component).toBeTruthy();
    } catch (error) {
      console.error('Error in should create:', error);
      throw error;
    }
  });

  it('all should be actores', () => {
    for (let i = 0; i<10;i++){
      let b:boolean = component.actores[i].rol === "Actriz" || component.actores[i].rol === "Actor" ;
      expect(b).toEqual(true);
    }
  });

  it('should have the corresponding data to the person', () => {
    debug.queryAll(By.css('.card.DataPerson')).forEach((fila, i)=>{
      expect(fila.query(By.css('img')).attributes["src"]).toEqual(
        component.actores[i].imagen);

      expect(fila.query(By.css('h5')).nativeElement.textContent).toEqual(
        component.actores[i].nombre);
    });
  });
});
