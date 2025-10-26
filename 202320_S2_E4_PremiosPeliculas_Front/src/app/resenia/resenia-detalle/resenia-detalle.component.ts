import { Component, Input, OnInit } from '@angular/core';
import { ReseniaDetail } from '../reseniaDetail';
import { ActivatedRoute } from '@angular/router';
import { ReseniaService } from '../resenia.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-resenia-detalle',
  templateUrl: './resenia-detalle.component.html',
  styleUrls: ['./resenia-detalle.component.css']
})
export class ReseniaDetalleComponent implements OnInit {
  reseniaID!: string;
  @Input() reseniaDetalle!: ReseniaDetail;
  constructor(private route: ActivatedRoute,
    private reseniaService: ReseniaService) { }

  getReseniaDetail(): void {
    this.reseniaService.getReseniaDetail(this.reseniaID).subscribe(reseniaDetail => {
      this.reseniaDetalle = reseniaDetail
    });
  }

  ngOnInit() {
    if (this.reseniaDetalle == undefined) {
      this.reseniaID = this.route.snapshot.paramMap.get('id')!;
      if (this.reseniaID) {
        this.getReseniaDetail();
        }
      }
    }
  }


