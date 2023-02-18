package es.e1sordo.worklog_sync.dto.notion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectsPageResponse {

    private List<ObjectResponse> results = new ArrayList<>();
}
