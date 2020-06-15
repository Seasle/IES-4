<template>
    <canvas ref="canvas"></canvas>
</template>

<script>
import Chart from 'chart.js';
import format from '@/modules/format.js';

export default {
    name: 'Chart',
    data() {
        return {
            chart: null
        };
    },
    mounted() {
        this.chart = new Chart(this.$refs.canvas, {
            type: 'line',
            data: {
                labels: this.createDateRange(
                    new Date(2020, 4, 3),
                    new Date(2020, 4, 9)
                ),
                datasets: [
                    {
                        data: this.createRange(7, 10, 80),
                        borderColor: 'rgb(148, 176, 219)',
                        backgroundColor: 'rgba(148, 176, 219, 0.5)'
                    }
                ]
            },
            options: {
                responsive: true,
                title: {
                    display: true,
                    text: 'Chart.js Line Chart'
                },
                legend: {
                    display: false
                },
                hover: {
                    mode: 'nearest',
                    intersect: true
                }
            }
        });
    },
    beforeDestroy() {
        this.chart.destroy();
    },
    methods: {
        random(min, max) {
            return Math.floor(Math.random() * (max - min + 1) + min);
        },
        createDateRange(start, end) {
            const day = 24 * 60 * 60 * 1000
            const count = (end.getTime() - start.getTime()) / day;
            const date = new Date(start.getTime());
            const labels = [];

            for (let index = 0; index <= count; index++) {
                date.setTime(start.getTime() + day * index);
                labels.push(format(date, '[day].[month].[year]'));
            }

            return labels;
        },
        createRange(count, min, max) {
            const data = [];

            for (let index = 0; index < count; index++) {
                data.push(this.random(min, max));
            }

            return data;
        }
    }
};
</script>

<style lang="scss" scoped>
canvas {}
</style>