/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { GanadorService } from './ganador.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('Service: Ganador', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [GanadorService]
    });
  });

  it('should ...', inject([GanadorService], (service: GanadorService) => {
    expect(service).toBeTruthy();
  }));
});
