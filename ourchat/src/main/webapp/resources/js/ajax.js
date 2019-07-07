function ajaxMake(url, params) {
    ajaxFunction(url, params, "put");
}

function ajaxEdit(url, params) {
    ajaxFunction(url, params, "post");
}

function ajaxRemove(url, params) {
    ajaxFunction(url, params, "delete");
}

function ajaxView(url, params) {
    ajaxFunction(url, params, "get");
}

function ajaxFunction(url, params, method) {
    $.ajax({
        url: url,
        data: params,
        type: method,
        beforeSend: function (xhr) {
            console.log("before " + method);
        },
        complete: function (jqXHR, textStatus) {
            console.log(method + " finished");
        },
        success: function (data, textStatus, jqXHR) {
            console.log(method + " success");
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(method + " error");
        },
        statusCode: {
            // data - json objects
            // ok
            200: function (data) {
                if (exists(data.redirectUrl)) {
                    window.location.href = data.redirectUrl;
                }
            },
            400: function (data) {
                if (exists(data.responseJSON.notification)) {
                    $("#js-error-message").text(data.responseJSON.notification);
                    setTimeout(function() {
                        $("#js-error-message").text("");
                    }, 5000);
                }
            }
        }

    });
}
