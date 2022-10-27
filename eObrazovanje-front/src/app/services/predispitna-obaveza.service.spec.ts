import { TestBed } from '@angular/core/testing';

import { PredispitnaObavezaService } from './predispitna-obaveza.service';

describe('PredispitnaObavezaService', () => {
  let service: PredispitnaObavezaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PredispitnaObavezaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
