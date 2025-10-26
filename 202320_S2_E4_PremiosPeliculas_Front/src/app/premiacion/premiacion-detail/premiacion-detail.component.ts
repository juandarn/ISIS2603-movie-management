import { Component,Input,OnInit } from '@angular/core';
import { PremiacionDetail } from '../premiacionDetail';
import { ActivatedRoute, Router } from '@angular/router';
import { PremiacionService } from '../premiacion.service';
import { PremioDetailDTO } from 'src/app/premio/premioDetail';

@Component({
  selector: 'app-premiacion-detail',
  templateUrl: './premiacion-detail.component.html',
  styleUrls: ['./premiacion-detail.component.css']
})
export class PremiacionDetailComponent implements OnInit {

  premiacionId !: string;
  isPremiacionPage : boolean = false;
  @Input() premiacionDetail!: PremiacionDetail;

  constructor(
    private route : ActivatedRoute,
    private premiacionService : PremiacionService,
    private router: Router
  ) { }

  getPremiacion(){
    this.premiacionService.getPremiacion(this.premiacionId).subscribe(premiacion => this.premiacionDetail = premiacion);
  }

  ngOnInit() {
    if(this.premiacionDetail === undefined){
      this.premiacionId = this.route.snapshot.paramMap.get('id')!;
      if(this.premiacionId){
        this.getPremiacion();
      }
    }
    this.isPremiacionPage = this.router.url === '/premiacion/list';

  }

}
