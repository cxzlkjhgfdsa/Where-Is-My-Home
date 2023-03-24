<template>
    <div id="map" class="pl-2"></div>
</template>

<script>
import { mapState } from "vuex";
const houseStore = "houseStore";
export default {
    name: "searchMap",
    data() {
        return {
            markerPositions: [],
            markers: [],
        };
    },
    methods: {
        initMap() {
            this.container = document.getElementById("map");

            const options = {
                center: new kakao.maps.LatLng(33.450701, 126.570667),
                level: 3,
            };

            this.map = new kakao.maps.Map(this.container, options);
        },
        displayMarkers(positions) {
            if (this.markers.length > 0) {
                this.markers.forEach((item) => {
                    item.setMap(null);
                });
            }

            const imgSrc = require("@/assets/marker.png");
            const imgSize = new kakao.maps.Size(35, 35);
            const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

            positions.forEach((position) => {
                const marker = new kakao.maps.Marker({
                    map: this.map,
                    position: position.latlng,
                    image: markerImage,
                });

                var content =
                    '<div class="customoverlay">' +
                    `  <a href="https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=${position.title}" target="_blank">` +
                    `    <span class="title">${position.title}</span>` +
                    "</div>";

                marker.setMap(this.map);

                const overlay = new kakao.maps.CustomOverlay({
                    map: this.map,
                    yAnchor: 0,
                    position: position.latlng,
                    content: content,
                    visible: false,
                });

                overlay.setMap(this.map);

                kakao.maps.event.addListener(marker, "click", function () {
                    overlay.setVisible(true);
                });

                kakao.maps.event.addListener(marker, "rightclick", function () {
                    overlay.setVisible(false);
                });

                this.markers.push(marker);
            });

            const bounds = positions.reduce(
                (bounds, position) => bounds.extend(position.latlng),
                new kakao.maps.LatLngBounds()
            );

            this.map.setBounds(bounds);
        },
    },
    mounted() {
        const script = document.createElement("script");
        /* global kakao */
        script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_KAKAOMAP_KEY}`;
        script.onload = () => {
            kakao.maps.load(this.initMap);
        };
        //console.log(process.env);
        document.head.appendChild(script);
    },
    computed: {
        ...mapState(houseStore, ["houses", "showResult", "checkVal", "center"]),
    },
    watch: {
        checkVal() {
            //console.log(1);
            if (this.markerPositions.length > 0) {
                this.markerPositions = [];
            }
            this.markerPositions = [];
            this.houses.forEach((item) => {
                let marker = {
                    title: item.aptName,
                    latlng: new kakao.maps.LatLng(item.lat, item.lng),
                };
                this.markerPositions.push(marker);
            });

            this.displayMarkers(this.markerPositions);
        },
        center() {
            var moveLoc = new kakao.maps.LatLng(this.center[0], this.center[1]);
            this.map.setLevel(2);
            this.map.panTo(moveLoc);
        },
    },
};
</script>

<style>
#map {
    width: 1300px;
    height: 600px;
}
.customoverlay {
    position: relative;
    bottom: 85px;
    border-radius: 6px;
    border: 1px solid #ccc;
    border-bottom: 2px solid #ddd;
    float: left;
}
.customoverlay:nth-of-type(n) {
    border: 0;
    box-shadow: 0px 1px 2px #888;
}
.customoverlay a {
    display: block;
    text-decoration: none;
    color: #000;
    text-align: center;
    border-radius: 6px;
    font-size: 14px;
    font-weight: bold;
    overflow: hidden;
    background: #d95050;
    background: #d95050
        url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png) no-repeat
        right 14px center;
}
.customoverlay .title {
    display: block;
    text-align: center;
    background: #fff;
    margin-right: 35px;
    padding: 10px 15px;
    font-size: 14px;
    font-weight: bold;
}
.customoverlay:after {
    content: "";
    position: absolute;
    margin-left: -12px;
    left: 50%;
    bottom: -12px;
    width: 22px;
    height: 12px;
    background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
</style>
