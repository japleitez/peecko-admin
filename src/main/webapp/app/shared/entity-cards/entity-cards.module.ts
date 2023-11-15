import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomerCardComponent } from './customer-card/customer-card.component';



@NgModule({
    declarations: [
        CustomerCardComponent
    ],
    exports: [
        CustomerCardComponent
    ],
    imports: [
        CommonModule
    ]
})
export class EntityCardsModule { }
