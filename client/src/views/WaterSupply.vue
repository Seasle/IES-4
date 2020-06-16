<template>
    <div class="wrapper">
        <Chart
            :title="'Потребление холодной воды'"
            :type="'line'"
            :labels="labels"
            :data="coldWaterConsumption"
        />
        <Chart
            :title="'Потребление горячей воды'"
            :type="'line'"
            :labels="labels"
            :data="hotWaterConsumption"
        />
    </div>
</template>

<script>
import Chart from '@/components/Chart.vue';
import format from '@/modules/format.js';

export default {
    name: 'WaterSupply',
    props: ['data'],
    components: {
        Chart
    },
    computed: {
        labels() {
            return this.data.map(entry => format(new Date(entry.timestamp), '[day].[month].[year]'));
        },
        coldWaterConsumption() {
            return this.data.map(entry => entry.coldWaterConsumption);
        },
        hotWaterConsumption() {
            return this.data.map(entry => entry.hotWaterConsumption);
        }
    }
};
</script>