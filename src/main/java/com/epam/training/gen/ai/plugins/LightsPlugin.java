package com.epam.training.gen.ai.plugins;

import com.microsoft.semantickernel.semanticfunctions.annotations.DefineKernelFunction;
import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LightsPlugin {

  private static final Logger LOGGER = LoggerFactory.getLogger(LightsPlugin.class);

  public record LightModel(Integer id, String name, Boolean active) {
    public LightModel setIsOn(Boolean active) {
      return new LightModel(this.id, this.name, active);
    }
  }

  private final Map<Integer, LightModel> lights;

  public LightsPlugin() {
    this.lights = HashMap.newHashMap(3);
    this.lights.put(1, new LightModel(1, "Table Lamp", false));
    this.lights.put(2, new LightModel(2, "Porch light", false));
    this.lights.put(3, new LightModel(3, "Chandelier", true));
  }

  @DefineKernelFunction(name = "get_lights", description = "Gets a list of lights and their current state")
  public List<LightModel> getLights() {
    LOGGER.info("Retrieving all the lights");
    return new ArrayList<>(this.lights.values());
  }

  @DefineKernelFunction(name = "change_state", description = "Changes the state of the light")
  public LightModel changeState(
    @KernelFunctionParameter(name = "id", description = "The ID of the light to change") Integer id,
    @KernelFunctionParameter(name = "isOn", description = "The new state of the light") Boolean isOn) {
    LOGGER.info("Changing light {} {}", id, isOn);

    if (!this.lights.containsKey(id)) {
      throw new IllegalArgumentException("Light not found");
    }

    this.lights.computeIfPresent(id, (_, model) -> model.setIsOn(isOn));

    return this.lights.get(id);
  }
}