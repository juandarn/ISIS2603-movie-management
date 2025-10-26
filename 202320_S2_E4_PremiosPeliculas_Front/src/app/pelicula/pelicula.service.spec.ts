/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { PeliculaService } from './pelicula.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('Service: Pelicula', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [PeliculaService]
    });
  });

  it('should ...', inject([PeliculaService], (service: PeliculaService) => {
    expect(service).toBeTruthy();
  }));
});
