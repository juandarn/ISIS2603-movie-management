/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { PremiacionService } from './premiacion.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';

describe('Service: Premiacion', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [PremiacionService]
    });
  });

  it('should ...', inject([PremiacionService], (service: PremiacionService) => {
    expect(service).toBeTruthy();
  }));
});
