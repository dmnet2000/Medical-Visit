import {createRouter, createWebHistory} from 'vue-router';
import Home from '../component/view/Home.vue';
import Login from '@/component/view/Login.vue';
import NotFound from '@/component/view/NotFound.vue';
import Import from '@/component/search/Import.vue';
import SearchForm from '@/component/search/SearchForm.vue';
import ModifyAtleta from '@/component/crud/ModifyAtleta.vue';
import CoachManager from '@/component/view/CoachManager.vue';
import AnnoAgonisticoManager from '@/component/view/AnnoAgonisticoManager.vue';
import SquadreManager from '@/component/view/SquadreManager.vue';
import SquadraAtleti from '@/component/view/SquadraAtleti.vue';

const routes = [
    { path: '/home', component: Home , children: [ {path: 'import', component: Import },
                                                   {path: 'coach', component: CoachManager },
                                                   {path: 'search', component: SearchForm },
                                                   {path: 'search/:id/modifica', component: ModifyAtleta},
                                                   {path: 'anno', component: AnnoAgonisticoManager },
                                                   {path: 'squadre', component: SquadreManager },
                                                   {path: 'squadre/:id/atleti', name:"AssociaAtleti", component: SquadraAtleti, props: true}]},
    { path:'/login', component: Login },
    { path: '/', component: Login },
    
    //{ path: '/search', component: Search },
    { path: '/:pathMatch(.*)', name: 'bad-not-found', component: NotFound },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;