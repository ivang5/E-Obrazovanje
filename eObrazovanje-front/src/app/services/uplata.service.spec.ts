import { TestBed } from '@angular/core/testing';

import { UplataService } from './uplata.service';

describe('UplataService', () => {
  let service: UplataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UplataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
