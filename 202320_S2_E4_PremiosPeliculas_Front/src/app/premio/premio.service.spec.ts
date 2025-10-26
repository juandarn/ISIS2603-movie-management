/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { PremioService } from './premio.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('Service: Premio', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [PremioService]
    });
  });

  it('should ...', inject([PremioService], (service: PremioService) => {
    expect(service).toBeTruthy();
  }));
});
