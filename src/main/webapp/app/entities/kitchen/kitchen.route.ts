import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Kitchen } from 'app/shared/model/kitchen.model';
import { KitchenService } from './kitchen.service';
import { KitchenComponent } from './kitchen.component';
import { KitchenDetailComponent } from './kitchen-detail.component';
import { KitchenUpdateComponent } from './kitchen-update.component';
import { KitchenDeletePopupComponent } from './kitchen-delete-dialog.component';
import { IKitchen } from 'app/shared/model/kitchen.model';

@Injectable({ providedIn: 'root' })
export class KitchenResolve implements Resolve<IKitchen> {
    constructor(private service: KitchenService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Kitchen> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Kitchen>) => response.ok),
                map((kitchen: HttpResponse<Kitchen>) => kitchen.body)
            );
        }
        return of(new Kitchen());
    }
}

export const kitchenRoute: Routes = [
    {
        path: 'kitchen',
        component: KitchenComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Kitchens'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'kitchen/:id/view',
        component: KitchenDetailComponent,
        resolve: {
            kitchen: KitchenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Kitchens'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'kitchen/new',
        component: KitchenUpdateComponent,
        resolve: {
            kitchen: KitchenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Kitchens'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'kitchen/:id/edit',
        component: KitchenUpdateComponent,
        resolve: {
            kitchen: KitchenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Kitchens'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const kitchenPopupRoute: Routes = [
    {
        path: 'kitchen/:id/delete',
        component: KitchenDeletePopupComponent,
        resolve: {
            kitchen: KitchenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Kitchens'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
