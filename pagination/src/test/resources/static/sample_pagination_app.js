var app = angular.module('samplePaginationApp', []);

app
    .factory('pagerService', PagerService)
    .controller('samplePaginationDataCtrl', ['$scope', '$window', '$http', 'pagerService', function($scope, $window, $http, PagerService) {
    $scope.pager = {};
    $scope.debugMode = true;
    $scope.error = "";
  
    $scope.init = function() {
        $scope.pager = PagerService.GetPager(0, 1, 20);
        if ($scope.debugMode) {
            console.log($scope.pager);
        }
        $scope.fetchData();
    };
    
    $scope.setPage = function(page) {
        if ($scope.debugMode) {
            console.log("page: " + page);
        }
        if (page < 1 || page > $scope.pager.totalPages) {
            return;
        }
 
        // get pager object from service
        $scope.pager = PagerService.GetPager($scope.pager.totalItems, page, 20);
    }
    
    $scope.fetchData = function() {
        if ($scope.debugMode) {
        }
        var proxy_url = "http://localhost:8080/getPaginatedTestTable?page=" + $scope.pager.currentPage; 
                
        $http({
            method: "GET",
            url: proxy_url
        }).then(
            function successCallBack(response) {
                if ($scope.debugMode) {
                    console.log(response);
                    console.log('json: ' + response);
                }
                $scope.announcementDatas = response.data;
                $scope.pager = PagerService.GetPager($scope.announcementDatas.total, $scope.pager.currentPage, $scope.announcementDatas.pageSize);
                if ($scope.debugMode) {
                    console.log($scope.pager);
                }
            },
            function errorCallBack(response) {
                if ($scope.debugMode) {
                    console.log(response);
                }
                $scope.error = JSON.stringify(response);
            }
        );
    };
    
}]);

function PagerService() {
    // service definition
    var service = {};
    
    service.GetPager = GetPager;
    
    return service;
    
    // service implementation
    function GetPager(totalItems, currentPage, pageSize) {
        // default to first page
        currentPage = currentPage || 1;
    
        // default page size is 10
        pageSize = pageSize || 10;
    
        // calculate total pages
        var totalPages = Math.ceil(totalItems / pageSize);
    
        var startPage, endPage;
        if (totalPages <= 10) {
            // less than 10 total pages so show all
            startPage = 1;
            endPage = totalPages;
        } else {
            // more than 10 total pages so calculate start and end pages
            if (currentPage <= 6) {
                startPage = 1;
                endPage = 10;
            } else if (currentPage + 4 >= totalPages) {
                startPage = totalPages - 9;
                endPage = totalPages;
            } else {
                startPage = currentPage - 5;
                endPage = currentPage + 4;
            }
        }
    
        // calculate start and end item indexes
        var startIndex = (currentPage - 1) * pageSize;
        var endIndex = Math.min(startIndex + pageSize - 1, totalItems - 1);
    
        // create an array of pages to ng-repeat in the pager control
        var pages = _.range(startPage, endPage + 1);
    
        // return object with all pager properties required by the view
        return {
            totalItems: totalItems,
            currentPage: currentPage,
            pageSize: pageSize,
            totalPages: totalPages,
            startPage: startPage,
            endPage: endPage,
            startIndex: startIndex,
            endIndex: endIndex,
            pages: pages
        };
    }
}

angular.element(document).ready(function() {
    angular.bootstrap(document.getElementById("samplePaginationApp"), ['samplePaginationApp']);
});