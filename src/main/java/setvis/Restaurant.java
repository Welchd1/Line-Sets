/* This file is Copyright 2014 Dan Welch
 * 
 * This file is part of 'LineSets', a final project for cpsc804: Data
 * Visualization.
 * 
 * LineSets is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * LineSets is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with LineSets. If not, see http://www.gnu.org/licenses/.
 */
package setvis;

import de.fhpotsdam.unfolding.geo.Location;

/**
 * <p>A <code>Restaurant</code> encapsulates all relevant metadata pulled
 * from the original {@link processing.data.JSONObject}, as found in the
 * original <tt>yelpAPI</tt> search results.</p>
 */
public class Restaurant {

    public static enum RestaurantType implements SubCategory {
        AMERICAN {

            @Override
            protected String[] getValidSynonyms() {
                return new String[] { "cajun", "american", "hotdogs" };
            }

            @Override
            public Integer getColor() {
                return 0xFFF2003C;
            }
        },
        ITALIAN {

            @Override
            protected String[] getValidSynonyms() {
                return new String[] { "italian", "pizza" };
            }

            @Override
            public Integer getColor() {
                return 0xFFFDC086;
            }
        },
        ASIAN {

            @Override
            protected String[] getValidSynonyms() {
                return new String[] { "korean", "japanese", "chinese",
                        "vietnamese", "asianfusion" };
            }

            @Override
            public Integer getColor() {
                return -8402561;
            }
        },
        MEXICAN {

            @Override
            protected String[] getValidSynonyms() {
                return new String[] { "spanish", "mexican" };
            }

            @Override
            public Integer getColor() {
                return -13079376;
            }
        };

        protected abstract String[] getValidSynonyms();

        public boolean acceptableFor(String candidate) {
            String[] synonyms = getValidSynonyms();

            for (String synonym : synonyms) {
                if (candidate.toLowerCase().contains(synonym)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String getParentCategoryDescriptor() {
            return "restaurant type";
        }
    }

    private final String myName, myID;
    private final Location myLocation;

    private final RestaurantType myType;

    private Restaurant(RestaurantBuilder builder) {
        myName = builder.myName;
        myID = builder.myID;
        myLocation = builder.myLocation;
        myType = builder.myType;
    }

    public Location getLocation() {
        return myLocation;
    }

    public RestaurantType getType() {
        return myType;
    }

    public String getRestaurantName() {
        return myName;
    }

    public String getID() {
        return myID;
    }

    public int hashCode() {
        return myID.hashCode();
    }

    public static class RestaurantBuilder implements Builder<Restaurant> {

        private String myName, myID;
        private Location myLocation;

        private RestaurantType myType;

        public RestaurantBuilder(String name) {
            myName = name;
        }

        public RestaurantBuilder location(float latitude, float longitude) {
            return location(new Location(latitude, longitude));
        }

        public RestaurantBuilder location(Location location) {
            myLocation = location;
            return this;
        }

        public RestaurantBuilder id(String id) {
            myID = id;
            return this;
        }

        public RestaurantBuilder type(RestaurantType type) {
            myType = type;
            return this;
        }

        @Override
        public Restaurant build() {
            if (myType == null) {
                throw new IllegalStateException("Null restaurant type. All"
                        + " restaurants must have a type.");
            }

            if (myLocation == null) {
                throw new IllegalStateException("Null location. All"
                        + " restaurants must have a valid location.");
            }
            return new Restaurant(this);
        }
    }
}
