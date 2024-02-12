    dropdownBrands = $("#brand");
    dropdownCategories = $("#category")

    $(document).ready(function(){
        $("#shortDescription").richText();
        $("#fullDescription").richText();

        dropdownBrands.change(function(){
            dropdownCategories.empty();
            getCategories();
        });
        getCategories();

        $("#extraImage1").change(function(){
                    fileSize = this.files[0].size;

                    if (fileSize > 1048576){
                        this.setCustomValidity("You must choose image less than 1MB!");
                        this.reportValidity();
                    }else {
                        this.setCustomValidity("");
                        showImageThumbnail(this);
                    }
                });
    });

    function showExtraImageThumbnail(fileInput){
        var file = fileInput.files[0];
                var reader = new FileReader();
                reader.onload = function(e) {
                    $("#extraThumbnail1").attr("src", e.target.result);
                };
                reader.readAsDataURL(file);

                addExtraImagesSection();
    }

    function addExtraImagesSection(){
        html = `
            <div class="col border m-3 p-2">
                                <div>
                                    <label>Extra Image #2:</label>
                                </div>
                                <div class="m-2">
                                    <img id="extraThumbnail2" alt="Extra image #2 preview" class="img-fluid"
                                         th:src="@{/images/image-thumbnail.png}"/>
                                </div>
                                <div>
                                    <input type="file" id="extraImage2" name="extraImage2"
                                           accept="image/png, image/jpeg"/>
                                </div>
                            </div>
                `;
                $("#divProductImages").append(html);
    }

    function getCategories(){
        brandId = dropdownBrands.val();
        url = brandModuleURL + "/" + brandId + "/categories";

        $.get(url, function(responseJson){
            $.each(responseJson, function(index, category){
                $("<option>").val(category.id).text(category.name).appendTo(dropdownCategories);
            });
        });
    }

    function checkUnique(form) {
        productId = $("#id").val();
        productName = $("#name").val();

        csrfValue = $("input[name='_csrf']").val();

        url = "[[@{/products/check_unique}]]";

        params = {id: productId, name: productName, _csrf: csrfValue};

        $.post(url, params, function(response){
            if (response == "OK"){
                form.submit();
            } else if (response == "Duplicated!") {
                showWarningModal("There is another product having the name" + productName);
            } else {
                showErrorModal("Unknown response from server");
            }
        }).fail(function() {
            showErrorModal("Could not connect to the server");
        });
        return false;
    }