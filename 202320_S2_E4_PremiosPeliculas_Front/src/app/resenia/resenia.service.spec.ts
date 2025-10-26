/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { ReseniaService } from './resenia.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('Service: Resenia', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ReseniaService]
    });
  });

  it('should ...', inject([ReseniaService], (service: ReseniaService) => {
    expect(service).toBeTruthy();
  }));
});
