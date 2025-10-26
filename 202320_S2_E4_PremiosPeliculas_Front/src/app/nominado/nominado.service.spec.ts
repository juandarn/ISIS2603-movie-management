/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { NominadoService } from './nominado.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('Service: Nominado', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [NominadoService]
    });
  });

  it('should ...', inject([NominadoService], (service: NominadoService) => {
    expect(service).toBeTruthy();
  }));
});
