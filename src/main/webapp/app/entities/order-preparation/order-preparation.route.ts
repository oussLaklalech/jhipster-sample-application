import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OrderPreparation } from 'app/shared/model/order-preparation.model';
import { OrderPreparationService } from './order-preparation.service';
import { OrderPreparationComponent } from './order-preparation.component';
import { OrderPreparationDetailComponent } from './order-preparation-detail.component';
import { OrderPreparationUpdateComponent } from './order-preparation-update.component';
import { OrderPreparationDeletePopupComponent } from './order-preparation-delete-dialog.component';
import { IOrderPreparation } from 'app/shared/model/order-preparation.model';

@Injectable({ providedIn: 'root' })
export class OrderPreparationResolve implements Resolve<IOrderPreparation> {
    constructor(private service: OrderPreparationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<OrderPreparation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<OrderPreparation>) => response.ok),
                map((orderPreparation: HttpResponse<OrderPreparation>) => orderPreparation.body)
            );
        }
        return of(new OrderPreparation());
    }
}

export const orderPreparationRoute: Routes = [
    {
        path: 'order-preparation',
        component: OrderPreparationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderPreparations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'order-preparation/:id/view',
        component: OrderPreparationDetailComponent,
        resolve: {
            orderPreparation: OrderPreparationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderPreparations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'order-preparation/new',
        component: OrderPreparationUpdateComponent,
        resolve: {
            orderPreparation: OrderPreparationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderPreparations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'order-preparation/:id/edit',
        component: OrderPreparationUpdateComponent,
        resolve: {
            orderPreparation: OrderPreparationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderPreparations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const orderPreparationPopupRoute: Routes = [
    {
        path: 'order-preparation/:id/delete',
        component: OrderPreparationDeletePopupComponent,
        resolve: {
            orderPreparation: OrderPreparationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderPreparations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
