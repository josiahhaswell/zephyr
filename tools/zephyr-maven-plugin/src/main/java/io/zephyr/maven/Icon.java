package io.zephyr.maven;

import io.zephyr.bundle.sfx.ConfigurationObject;
import io.zephyr.bundle.sfx.icons.IconComponent;
import lombok.val;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Icon implements ConfigurationObject<IconComponent> {

  enum Size {
    Size16px("16px", 16),
    Size24px("24px", 24),
    Size32px("32px", 32),
    Size48px("48px", 48);

    final int pixels;
    final String size;

    Size(String size, int pixels) {
      this.size = size;
      this.pixels = pixels;
    }

    public static Size fromString(String value) {
      for (val sizeEl : Size.values()) {
        if (sizeEl.size.equals(value)) {
          return sizeEl;
        }
      }
      throw new IllegalArgumentException("Error: invalid size: " + value);
    }

    public String toString() {
      return size;
    }
  }

  enum Channel {
    RGBA,
    EightBit;

    public static Channel fromString(String value) {
      val normalized = value.toLowerCase();
      switch (normalized) {
        case "rgba":
          return Channel.RGBA;
        case "8bit":
          return Channel.EightBit;
      }
      throw new IllegalStateException("Error: invalid channel: " + value);
    }
  }

  /** doesn't seem to be a way to provide custom mappings to enums */
  private Size _size;

  private Channel _channel;

  @Parameter(name = "size", alias = "size", property = "generate-sfx.size")
  private String size;

  @Parameter(name = "channel", alias = "channel", property = "generate-sfx.channel")
  private String channel;

  public void setSize(String size) {
    this.size = size;
    this._size = Size.valueOf(size);
  }

  public Size getSize() {
    if (_size == null && size != null) {
      setSize(size);
    }
    return _size;
  }

  public void setChannel(String channel) {
    this.channel = channel;
    this._channel = Channel.valueOf(channel);
  }

  public Channel getChannel() {
    if (_channel == null && channel != null) {
      setChannel(channel);
    }
    return _channel;
  }

  @Override
  public IconComponent toCoreObject() {
    val result = new IconComponent();
    result.setSize(getSize().toString());
    result.setChannel(getChannel().toString());
    return result;
  }

  public static List<IconComponent> mapIcons(List<Icon> icons) {
    if (icons == null) {
      return Collections.emptyList();
    }

    val result = new ArrayList<IconComponent>(icons.size());
    for (val icon : icons) {
      result.add(icon.toCoreObject());
    }
    return result;
  }
}
