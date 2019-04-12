package gabriellee.project.pixabaygallery.requests.responses;

public class CheckApiKey {

    protected static boolean isApiKeyValid(PictureResponse response) {
        return response.getError()==null;
    }

}
