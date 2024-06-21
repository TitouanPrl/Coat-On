package io.coaton.weather;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.coaton.weather.controller.WeatherController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(WeatherController.class)
public class WeatherControllerTests {
  final String LAT = "59.417129776724856";
  final String LON = "24.71145194604442";

  @MockBean
  private WeatherController weatherController;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void getCurrentWeatherApiTest() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders
                .get("/api/weather/current/lat=" + LAT + "&lon=" + LON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void getDailyWeatherApiTest() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders
                .get(String.format("/api/weather/daily/lat=%s&lon=%s", LAT, LON))
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}
