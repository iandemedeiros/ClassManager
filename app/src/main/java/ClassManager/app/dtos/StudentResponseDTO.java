package ClassManager.app.dtos;

public record StudentResponseDTO(
        String name,
        Integer ticket,
        Double defaultPrice,
        Boolean status) {
}
